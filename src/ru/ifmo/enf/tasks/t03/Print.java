package ru.ifmo.enf.tasks.t03;

import java.io.File;

/**
 * Created by May on 05.11.2014.
 */
public class Print {
	//Указывакт, где должен находиться файл

    public static void main(String[] args){
        File file =  new File("input");
        System.out.println(file.getAbsolutePath());
    }
}
