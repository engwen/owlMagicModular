package com.owl.pokemon;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/3/19.
 */
public class PokemonEnum {

    public enum PokemonAttr {//宝可梦主要属性
        INTELLECT, POWER, AGILE;

        PokemonAttr() {

        }
    }

    public enum Attribute {
        白(6, 1, 2, 3, 4, 5), 金(0, 1),
        木(1, 2, 4), 水(2, 3, 0),
        火(3, 4, 0), 土(4, 0, 2);
        private Integer attributeValue = null;//主属性
        private Integer[] suppressValue = null;//克制属性

        Attribute(Integer attributeValue, Integer... suppressValue) {
            this.attributeValue = attributeValue;
            this.suppressValue = suppressValue;
        }

        //是否克制
        public static boolean isSuppress(Attribute attacker, Attribute beatener) {

            if (attacker.equals(Attribute.白)) {
            }
            return attacker.attributeValue.equals(beatener.suppressValue);
        }

    }

    public enum Type {
        SUPPER("超能力", 0), SKY("飞行", 1), ground("地面", 2), rock("岩石", 3);

        Type(String name, Integer value) {

        }
    }

    public static void main(String[] args) {
        System.out.printf(Attribute.isSuppress(Attribute.土, Attribute.木) + "");
    }

}
