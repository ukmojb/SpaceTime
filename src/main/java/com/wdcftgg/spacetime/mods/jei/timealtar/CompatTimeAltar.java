package com.wdcftgg.spacetime.mods.jei.timealtar;

import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:33
 */
public class CompatTimeAltar extends Compat {

    @Override
    public void addRecipes(List<Wrapper> list) {
      for (int i = 0; i < TimeAltarCoreEntity.TimeAltarRecipesin.size(); i++) {
            list.add(new Wrapper((int) TimeAltarCoreEntity.TimeAltarRecipesTimeEnergy.get(i) + 6, TimeAltarCoreEntity.TimeAltarRecipesEnergy.get(TimeAltarCoreEntity.TimeAltarRecipesout.get(i)), "spacetime.jei.altar", TimeAltarCoreEntity.TimeAltarRecipesout.get(i), TimeAltarCoreEntity.TimeAltarRecipesin.get(i)));
        }
    }

    private List<Integer> newArray(int... num1){
        List<Integer> numList = new ArrayList<Integer>();
        for (int i : num1) {
            numList.add(i);
        }
        return numList;
    }


}
