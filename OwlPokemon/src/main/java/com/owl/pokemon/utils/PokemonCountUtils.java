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
    public static Float countBlood(Float power) {
        return power * 15;
    }

    /**
     * 物理伤害
     * @param attack 物攻
     * @param armor  敌人护甲
     * @return 伤害
     */
    public static Float countPubAttack(Float attack, Float armor) {
        if (armor >= 60F) {
            return PokemonCountUtils.countAttack(attack, armor, 0.9F + armor / 10000F);
        }
        return PokemonCountUtils.countAttack(attack, armor, 0.04F);
    }

    /**
     * 计算公式
     * @param attack 攻击
     * @param armor  护甲
     * @param xs     系数
     * @return 伤害
     */
    private static Float countAttack(Float attack, Float armor, Float xs) {
        return attack * (1 - xs * armor / (1 + xs * armor));
    }

    public static Float countMagicAttack(Float attack, Float intellect, Float power, Float agile, Float resistance) {
        resistance = resistance + countResistance(intellect, power, agile);
        return countAttack(attack, resistance, 0.001F);
    }


    /**
     * 伤害计算
     * @param intellect 智力
     * @param power     力量
     * @param agile     冥界
     * @param mainValue 主属性
     * @return 结果
     */
    public static Float countAttack(Float intellect, Float power, Float agile, int mainValue) {
        Float result = 0F;
        if (mainValue == 1) {
            result = intellect + (power + agile) * 0.2F;
        } else if (mainValue == 2) {
            result = power + (intellect + agile) * 0.2F;
        } else if (mainValue == 3) {
            result = agile + (intellect + power) * 0.2F;
        } else if (mainValue == 4) {
            result = (agile + intellect + power) * 0.4F;
        }
        return result;
    }

    /**
     * 抗性
     */
    public static Float countResistance(Float intellect, Float power, Float agile) {
        return intellect * 0.9F + power * 0.3F + agile * 0.1F;
    }

    public static void main(String[] args) {
        System.out.println(countPubAttack(65F, 8F));
        System.out.println(countAttack(60F, 35F, 35F, 1));
        System.out.println(countAttack(40F, 40F, 40F, 4));

        System.out.println(0.02 + (0.005 * 1000 / (50 + 0.001 * 1000)));
//        for (int i = 1; i <= 1000; i++) {
//            System.out.println(countResistance(i * 1D,1000D,1000D));
//            Double aDouble = countMagicAttack(300D, i * 1D, i * 1D, i * 1D);
//            if (aDouble < 150) {
//                System.out.println(i);
//                break;
//            }
//            System.out.println(countMagicAttack(300D, i * 1D, i * 1D, i * 1D));
//        }
    }
}
