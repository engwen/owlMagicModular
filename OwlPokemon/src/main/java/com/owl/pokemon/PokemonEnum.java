package com.owl.pokemon;

import com.owl.pokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/3/19.
 */
public class PokemonEnum {

    public static void main(String[] args) {
//        for (int i = 0; i < 10000; i++) {
//            int randomNum = RandomUtil.getRandomNum(0, 999);
//            if( randomNum == 99){
//                System.out.println(i);
//                break;
//            }
//        }

//        ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
//        String str = "2*3-45/5+9+9%4";
//        try {
//            Double d = Double.parseDouble(se.eval(str).toString());
//            System.out.println(d);
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }

        List<Pokemon> a = new ArrayList<>();
        a.sort(Comparator.comparing(Pokemon::getPower));
        System.out.println(a);
    }

}
