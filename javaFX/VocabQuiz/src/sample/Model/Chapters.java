package sample.Model;

public class Chapters {
    private static final int CHAPTERS = 3;
    private int[] wordForChapter = new int[CHAPTERS];
    public Chapters(int i1, int i2, int i3) {
        wordForChapter[0] = i1;
        wordForChapter[1] = i2;
        wordForChapter[2] = i3;
    }
    public void setChapterWord(int index, int wordCount) {
        wordForChapter[index] = wordCount;
    }
    public int getChapterWord(int index) {
        return wordForChapter[index];
    }
}
