package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.List;
import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        //checking inputs
        List<CombatNode> childrenA = teamA.getChildren();
        List<CombatNode> childrenB = teamB.getChildren();
        if(childrenA.isEmpty() || childrenB.isEmpty()) {
            RaidResult fail = new RaidResult();
            fail.setWinner("One of the teams is empty");
            fail.setRounds(0);
            fail.addLine("Error in team input");
            return fail;
        }
        if(!teamA.isAlive() || !teamB.isAlive()){
            RaidResult fail = new RaidResult();
            fail.setWinner("One of the teams is not alive");
            fail.setRounds(0);
            fail.addLine("Error in team input");
            return fail;
        }
        if(teamASkill == null  || teamBSkill == null){
            RaidResult fail = new RaidResult();
            fail.setWinner("One of the teams does not have a skill");
            fail.setRounds(0);
            fail.addLine("Error in skill input");
            return fail;
        }

        int rounds = 0;
        RaidResult result = new RaidResult();

        while(teamA.isAlive() && teamB.isAlive()) {
            rounds++;
            result.addLine("Round " + rounds + ":");
            boolean critTeamA = random.nextInt(100) < 20;
            if(critTeamA){
                teamASkill.cast(teamB, 1.5);
            }
            else {
                teamASkill.cast(teamB);
            }
            result.addLine(teamA.getName() + "► used [" + teamASkill.getEffectName() + " " + teamASkill.getSkillName() + "] on \"" + teamB.getName() + "\"");
            if(critTeamA) {
                result.addLine("\t\"" + teamB.getName() + "\" took " + (int) (teamASkill.resolvedDamage()*1.5) + " damage");
            }
            else{
                result.addLine("\t\"" + teamB.getName() + "\" took " + teamASkill.resolvedDamage() + " damage");
            }
            if(teamB.isAlive()) {
                teamBSkill.cast(teamA);
                result.addLine(teamB.getName() + "> used [" + teamBSkill.getEffectName() + teamBSkill.getSkillName() + "] on \"" + teamA.getName() + "\"");
                result.addLine("\t\"" + teamA.getName() + "\" took " + teamBSkill.resolvedDamage() + " damage\n");
            }
        }
        result.setRounds(rounds);
        if(teamA.isAlive()) {
            result.setWinner(teamA.getName());
            result.addLine(teamA.getName() + " won!");
        }
        if(teamB.isAlive()) {
            result.setWinner(teamB.getName());
            result.addLine(teamB.getName() + " won!");
        }
        return result;
        // Validate inputs (null checks, alive checks, required skills).
        // Implement round-based simulation:
        // 1) Team A casts on Team B
        // 2) Team B casts on Team A (if still alive)
        // 3) Track rounds and log each step
        // 4) Stop when one team is defeated (or max rounds reached)
        //
        // Optional extension:
        // Use random for critical strikes or other deterministic events.
        // Example: boolean critA = random.nextInt(100) < 10;
    }
}
