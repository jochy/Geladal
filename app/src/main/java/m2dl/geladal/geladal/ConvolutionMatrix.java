package m2dl.geladal.geladal;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ConvolutionMatrix {
    final static int KERNAL_WIDTH = 3;
    final static int KERNAL_HEIGHT = 3;

    //test one filter
    static int[][] kernalBlur ={
            {0, 1, 0},
            {1, -4, 1},
            {0, 1, 0}
    };


    private static void oneLine(int i, Bitmap src, Bitmap dest, int bmHeight_MINUS_2, int knl[][]) {
        for(int j = 1; j <= bmHeight_MINUS_2; j++){

            //get the surround 3*3 pixel of current src[i][j] into a matrix subSrc[][]
            int[][] subSrc = new int[KERNAL_WIDTH][KERNAL_HEIGHT];
            for(int k = 0; k < KERNAL_WIDTH; k++){
                for(int l = 0; l < KERNAL_HEIGHT; l++){
                    subSrc[k][l] = src.getPixel(i-1+k, j-1+l);
                }
            }

            //subSum = subSrc[][] * knl[][]
            int subSumA = 0;
            int subSumR = 0;
            int subSumG = 0;
            int subSumB = 0;


            for(int k = 0; k < KERNAL_WIDTH; k++){
                for(int l = 0; l < KERNAL_HEIGHT; l++){
                    subSumA += Color.alpha(subSrc[k][l]) * knl[k][l];
                    subSumR += Color.red(subSrc[k][l]) * knl[k][l];
                    subSumG += Color.green(subSrc[k][l]) * knl[k][l];
                    subSumB += Color.blue(subSrc[k][l]) * knl[k][l];
                }
            }

            if(subSumA<0){
                subSumA = 0;
            }else if(subSumA>255){
                subSumA = 255;
            }

            if(subSumR<0){
                subSumR = 0;
            }else if(subSumR>255){
                subSumR = 255;
            }

            if(subSumG<0){
                subSumG = 0;
            }else if(subSumG>255){
                subSumG = 255;
            }

            if(subSumB<0){
                subSumB = 0;
            }else if(subSumB>255){
                subSumB = 255;
            }

            dest.setPixel(i, j, Color.argb(
                    subSumA,
                    subSumR,
                    subSumG,
                    subSumB));
        }
    }


    public static Bitmap processingBitmap(final Bitmap src, final int[][] knl){
        final Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        final int bmWidth_MINUS_2 = bmWidth - 2;
        int bmHeight_MINUS_2 = bmHeight - 2;

        for(int i = 1; i <= bmWidth_MINUS_2; i++){
            final int i2 = i;
            new Runnable() {

                @Override
                public void run() {
                    oneLine(i2, src, dest, bmWidth_MINUS_2, knl);
                }
            };

            /*for(int j = 1; j <= bmHeight_MINUS_2; j++){

                //get the surround 3*3 pixel of current src[i][j] into a matrix subSrc[][]
                int[][] subSrc = new int[KERNAL_WIDTH][KERNAL_HEIGHT];
                for(int k = 0; k < KERNAL_WIDTH; k++){
                    for(int l = 0; l < KERNAL_HEIGHT; l++){
                        subSrc[k][l] = src.getPixel(i-1+k, j-1+l);
                    }
                }

                //subSum = subSrc[][] * knl[][]
                int subSumA = 0;
                int subSumR = 0;
                int subSumG = 0;
                int subSumB = 0;


                for(int k = 0; k < KERNAL_WIDTH; k++){
                    for(int l = 0; l < KERNAL_HEIGHT; l++){
                        subSumA += Color.alpha(subSrc[k][l]) * knl[k][l];
                        subSumR += Color.red(subSrc[k][l]) * knl[k][l];
                        subSumG += Color.green(subSrc[k][l]) * knl[k][l];
                        subSumB += Color.blue(subSrc[k][l]) * knl[k][l];
                    }
                }

                if(subSumA<0){
                    subSumA = 0;
                }else if(subSumA>255){
                    subSumA = 255;
                }

                if(subSumR<0){
                    subSumR = 0;
                }else if(subSumR>255){
                    subSumR = 255;
                }

                if(subSumG<0){
                    subSumG = 0;
                }else if(subSumG>255){
                    subSumG = 255;
                }

                if(subSumB<0){
                    subSumB = 0;
                }else if(subSumB>255){
                    subSumB = 255;
                }

                dest.setPixel(i, j, Color.argb(
                        subSumA,
                        subSumR,
                        subSumG,
                        subSumB));
            }*/
        }

        return dest;
    }
}
