class NotificationThread extends Thread {
    private String message;
    private HostelNoticeBoard board;

    NotificationThread(String message, HostelNoticeBoard board) {
        this.message = HostelMember.requireText(message, "Notice");
        if (board == null) throw new IllegalArgumentException("Notice board is required.");
        this.board = board;
    }

    @Override
    public void run() {
        board.postNotice(message);
    }
}
