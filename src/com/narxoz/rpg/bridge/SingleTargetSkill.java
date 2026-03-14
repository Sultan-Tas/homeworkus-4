package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SingleTargetSkill extends Skill {
    public SingleTargetSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target, double damageModifier){
        List<CombatNode> children = target.getChildren();
        List<CombatNode> aliveChildren = new ArrayList<>();
        Queue<CombatNode> queue = new LinkedList<>();
        queue.addAll(children);
        while(!queue.isEmpty()){
            CombatNode current = queue.remove();
            if(current.getChildren().isEmpty() && current.isAlive()){
                aliveChildren.add(current);
            }
            else{
                queue.addAll(current.getChildren());
            }
        }
        if(aliveChildren.isEmpty()){
            return;
        }
        else{
            aliveChildren.get(0).takeDamage((int) Math.round(resolvedDamage()*1.5));
        }
    }
    @Override
    public void cast(CombatNode target) {
        //drills down to find single target with no children (finds leaf)
        List<CombatNode> children = target.getChildren();
        List<CombatNode> aliveChildren = new ArrayList<>();
        Queue<CombatNode> queue = new LinkedList<>();
        queue.addAll(children);
        while(!queue.isEmpty()){
            CombatNode current = queue.remove();
            if(current.getChildren().isEmpty() && current.isAlive()){
                aliveChildren.add(current);
            }
            else{
                queue.addAll(current.getChildren());
            }
        }
        if(aliveChildren.isEmpty()){
            return;
        }
        else{
            aliveChildren.get(0).takeDamage(resolvedDamage());
        }
        // Single-target Bridge action
        // 1) Resolve final damage through effect implementor
        // 2) Apply to target node
    }
}
