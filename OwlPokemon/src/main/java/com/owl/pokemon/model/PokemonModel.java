package com.owl.pokemon.model;

import com.owl.pokemon.PokemonEnum;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/3/18.
 */
public class PokemonModel {
    private Integer bloodBar; //= 200 + power * 20
    private Integer magicBar; //= 100 + intellect * 18
    private Double armor; //= agile * 0.15
    private Integer attack;//攻击力= 50 + pokemonAttr
    private Integer intellect;//智力
    private Integer power;//力量
    private Integer agile;//敏捷
    private PokemonEnum.PokemonAttr pokemonAttr;//主属性
    private Integer raceValue;//种族值
    private Integer effortValue;//努力值


    private PokemonEnum.Attribute attribute;//属性 金木水火土
    private PokemonEnum.Type type;//属性2


    public PokemonModel(Integer armor, Integer intellect, Integer power, Integer agile, PokemonEnum.PokemonAttr pokemonAttr, Integer raceValue, Integer effortValue, PokemonEnum.Attribute attribute, PokemonEnum.Type type) {
        this.bloodBar = 200 + power * 20;
        this.magicBar = 100 + intellect * 18;
        this.armor = agile * 0.15;
        this.intellect = intellect;
        this.power = power;
        this.agile = agile;
        this.pokemonAttr = pokemonAttr;
        this.raceValue = raceValue;
        this.effortValue = effortValue;
        this.attribute = attribute;
        this.type = type;
        attack = 0;
        switch (this.pokemonAttr) {
            case INTELLECT:
                attack = 50 + this.intellect;
            case POWER:
                attack = 50 + this.power;
            case AGILE:
                attack = 50 + this.agile;
        }
    }

    public Integer attackPokemon(PokemonModel beatener) {//攻击宝可梦
        return 0;
    }


}
