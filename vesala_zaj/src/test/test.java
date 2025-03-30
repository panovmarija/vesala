/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author USER
 */
public class test {
    public static void main(String[] args) {
        String a="a";
        List<String>l=new LinkedList<>();
        l.add("a");
        l.add("b");
        
        System.out.println(String.join(",", l));
    }
}
