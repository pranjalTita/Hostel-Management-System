class HostelNoticeBoard {
    // CHANGE 3: both threads safely update this shared counter.
    private int noticeCount = 0;

    synchronized void postNotice(String notice) {
        noticeCount++;
        System.out.println("[Hostel Notice " + noticeCount + "] " + notice);
    }

    synchronized int getNoticeCount() {
        return noticeCount;
    }
}
