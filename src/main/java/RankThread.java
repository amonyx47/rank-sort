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


        //zistime, ktora cast bloku zaciatocneho pola nam prislucha podla poradoveho cisla procesoru
        if (threadNum - 1 == currentThreadNum) {
            endIndex = arrayToSort.length;
        } else {
            endIndex = (currentThreadNum + 1) * blockSize;
        }

        //ideme od zaciatku urceneho bloku po koniec urceneho bloku
        for (int j = startIndex; j < endIndex; j++) {
            currentItem = arrayToSort[j];
            currentPosition = 0; //pamatame si poziciu, ak by sa vyskytlo viac rovnakych prvkov
            //ideme cez cele zaciatocne pole
            for (int i = 0; i < arrayToSort.length; i++) {
                //ak je prvok, ktory mame vo vonkajsom cykle vacsi zvacsime rank
                if (currentItem > arrayToSort[i]) {
                    currentPosition++;
                }
                //ak je prvok rovnaky (podmienkou j < i zistime, ci je za nim), zvysime rank
                if ((currentItem == arrayToSort[i]) && (j < i)) {
                    currentPosition++;
                }
            }
            System.out.println("Radim cislo " + currentItem + " na poziciu " + currentPosition);
            resultArray[currentPosition] = currentItem;
        }

    }

}