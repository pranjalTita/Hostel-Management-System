import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

// Requests are flat objects of strings or numbers. Responses can contain lists and objects.
class Json {
    private static final Pattern TOKEN = Pattern.compile(
        "\"(?:[^\"\\\\\\x00-\\x1f]|\\\\(?:[\"\\\\/bfnrt]|u[0-9a-fA-F]{4}))*\"|-?(?:0|[1-9][0-9]*)(?:\\.[0-9]+)?(?:[eE][+-]?[0-9]+)?|[{}:,]");
    static Map<String,String> parse(String body) {
        List<String> tokens = new ArrayList<>();
        Matcher m = TOKEN.matcher(body);
        int end = 0;
        while (m.find()) {
            if (!body.substring(end,m.start()).isBlank()) throw invalid();
            tokens.add(m.group()); end = m.end();
        }
        if (!body.substring(end).isBlank() || tokens.size()<2 ||
            !tokens.get(0).equals("{") || !tokens.get(tokens.size()-1).equals("}")) throw invalid();
        Map<String,String> fields = new HashMap<>();
        for (int i=1; i<tokens.size()-1; i+=4) {
            if (i+2>=tokens.size()-1 || !tokens.get(i).startsWith("\"") ||
                !tokens.get(i+1).equals(":")) throw invalid();
            String value=tokens.get(i+2);
            if ("{}:,".contains(value)) throw invalid();
            if (fields.put(decode(tokens.get(i)),decode(value))!=null) throw invalid();
            if (i+3<tokens.size()-1 && (!tokens.get(i+3).equals(",") || i+4==tokens.size()-1)) throw invalid();
        }
        return fields;
    }
    static IllegalArgumentException invalid() { return new IllegalArgumentException("Invalid JSON object."); }
    static String decode(String token) {
        if (!token.startsWith("\"")) return token;
        StringBuilder out=new StringBuilder();
        for(int i=1;i<token.length()-1;i++) {
            char c=token.charAt(i);
            if(c=='\\') {
                c=token.charAt(++i);
                if(c=='u') { c=(char)Integer.parseInt(token.substring(i+1,i+5),16); i+=4; }
                else {
                    int idx="bfnrt".indexOf(c);
                    if(idx>=0) c="\b\f\n\r\t".charAt(idx);
                }
            }
            out.append(c);
        }
        return out.toString();
    }
    static byte[] bytes(Object obj) { return encode(obj).getBytes(StandardCharsets.UTF_8); }
    static String encode(Object obj) {
        if(obj==null || obj instanceof Number || obj instanceof Boolean) return String.valueOf(obj);
        if(obj instanceof Map<?,?> map) {
            StringJoiner j=new StringJoiner(",","{","}");
            map.forEach((k,v)->j.add(encode(k)+":"+encode(v))); return j.toString();
        }
        if(obj instanceof Iterable<?> items) {
            StringJoiner j=new StringJoiner(",","[","]");
            for(Object item:items) j.add(encode(item)); return j.toString();
        }
        StringBuilder out=new StringBuilder("\"");
        for(char c:obj.toString().toCharArray()) {
            if(c=='"' || c=='\\') out.append('\\').append(c);
            else if(c<32 || Character.isSurrogate(c)) out.append(String.format("\\u%04x",(int)c));
            else out.append(c);
        }
        return out.append('"').toString();
    }
}
