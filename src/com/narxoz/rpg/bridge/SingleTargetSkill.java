package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

import java.util.List;

public class SingleTargetSkill extends Skill {
    public SingleTargetSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target, double damageModifier){
        List<CombatNode> children = target.getChildren();
        if(children.isEmpty()) {
            target.takeDamage((int) Math.round(resolvedDamage()*damageModifier));
        }
        else{
            target = children.get(0);
            target.takeDamage((int) Math.round(resolvedDamage()*damageModifier));
        }
    }
    @Override
    public void cast(CombatNode target) {
        //drills down to find single target with no children (finds leaf)
        List<CombatNode> children = target.getChildren();
        if(children.isEmpty()) {
            target.takeDamage(resolvedDamage());
        }
        else{
            target = children.get(0);
            target.takeDamage(resolvedDamage());
        }
        // Single-target Bridge action
        // 1) Resolve final damage through effect implementor
        // 2) Apply to target node
    }
}
