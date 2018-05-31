class RankThread extends Thread {
    private int[] arrayToSort;
    private int[] resultArray;
    private int threadNum;
    private int threadIndex;

    private int currentItem;
    private int currentPosition;
    private int startIndex;
    private int endIndex;
    private String threadName;
    private int currentThreadNum;

    //podla http://alecu.ase.ro/articles/ie_en_2005.pdf


    RankThread(int[] arrayToSort, int[] resultArray, int threadNum, int threadIndex) {
        this.arrayToSort = arrayToSort;
        this.resultArray = resultArray;
        this.threadNum = threadNum;
        this.threadIndex = threadIndex;

        this.currentItem = 0;
        this.currentPosition = 0;
        this.startIndex = 0;
        this.endIndex = 0;
        this.threadName = "";
        this.currentThreadNum = 0;

        System.out.println("Vytvoril som thread.");
    }

    @Override
    public void run() {
        threadName = Thread.currentThread().getName();
        currentThreadNum = threadIndex;

        int blockSize = (int) Math.ceil(arrayToSort.length / threadNum);
        startIndex = currentThreadNum * blockSize;

        System.out.println("Som thread: " + threadName);
        System.out.println("Moje poradove cislo: " + currentThreadNum);
        System.out.println("Velkost bloku, ktory rankujem: " + blockSize);

        if (threadNum - 1 == currentThreadNum) {
            endIndex = arrayToSort.length;
        } else {
            endIndex = (currentThreadNum + 1) * blockSize;
        }

        for (int j = startIndex; j < endIndex; j++) {
            currentItem = arrayToSort[j];
            currentPosition = 0;
            for (int i = 0; i < arrayToSort.length; i++) {
                if (currentItem > arrayToSort[i]) {
                    currentPosition++;
                }
                if ((currentItem == arrayToSort[i]) && (j < i)) {
                    currentPosition++;
                }
            }
            resultArray[currentPosition] = currentItem;
        }

    }

}