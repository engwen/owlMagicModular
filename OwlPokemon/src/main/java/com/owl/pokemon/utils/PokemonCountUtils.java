package com.owl.pokemon.utils;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2023/5/9.
 */
public abstract class PokemonCountUtils {

    /**
     * 血量计算
     * @param power 力量点数
     * @return 血量
     */
    public static Double countBlood(Double power) {
        return power * 15;
    }

    /**
     * 物理伤害
     * @param attack 物攻
     * @param armor  敌人护甲
     * @return 伤害
     */
    public static Double countPubAttack(Double attack, Double armor) {
        if (armor >= 100D) {
            return PokemonCountUtils.countAttack(attack, armor, 0.9D + armor / 10000D);
        }
        return PokemonCountUtils.countAttack(attack, armor, 0.02D);
    }

    /**
     * 计算公式
     * @param attack 攻击
     * @param armor  护甲
     * @param xs     系数
     * @return 伤害
     */
    private static Double countAttack(Double attack, Double armor, Double xs) {
        return attack * (1 - xs * armor / (1 + xs * armor));
    }

    public static Double countMagicAttack(Double attack, Double intellect, Double power, Double agile, Double resistance) {
        resistance = resistance + countResistance(intellect, power, agile);
        return countAttack(attack, resistance, 0.001D);
    }

    /**
     * 抗性
     */
    public static Double countResistance(Double intellect, Double power, Double agile) {
        return intellect * 0.9D + power * 0.3D + agile * 0.1D;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 1000; i++) {
//            System.out.println(countResistance(i * 1D,1000D,1000D));
//            Double aDouble = countMagicAttack(300D, i * 1D, i * 1D, i * 1D);
//            if (aDouble < 150) {
//                System.out.println(i);
//                break;
//            }
//            System.out.println(countMagicAttack(300D, i * 1D, i * 1D, i * 1D));
        }
    }
}
