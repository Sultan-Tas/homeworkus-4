package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target, double damageModifier){
        target.takeDamage((int) Math.round(resolvedDamage()*damageModifier));
    }

    @Override
    public void cast(CombatNode target) {
        //damage application is distributed across all children in PartyComposite logic
        target.takeDamage(resolvedDamage());
        // Area Bridge action
        // Apply resolved damage to a composite target.
        // Tip: Let Composite classes decide how to distribute AOE damage.
    }
}
