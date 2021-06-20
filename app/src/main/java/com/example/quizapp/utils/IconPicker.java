package com.example.quizapp.utils;

import com.example.quizapp.R;

public class IconPicker {

        private static int currentIcon=0;
        // private int currentColorIndex=0;
        public static int[] icons = {R.drawable.ic_education_1,
                                    R.drawable.ic_education_2,
                                    R.drawable.ic_education_3,
                                    R.drawable.ic_education_4,
                                    R.drawable.ic_education_5,
                                    R.drawable.ic_education_6};
        public static int getIcon(){
            currentIcon=(currentIcon+1)%6;
            return icons[currentIcon];
        }


}
