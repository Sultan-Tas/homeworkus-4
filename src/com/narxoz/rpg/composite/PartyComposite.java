package com.narxoz.rpg.composite;

import java.util.*;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        int totalHP = 0;
        for(CombatNode unit : children){
            totalHP += unit.getHealth();
        }
        return totalHP;
    }

    @Override
    public int getAttackPower() {
        int totalAttack = 0;
        for(CombatNode unit : children){
            totalAttack += unit.getAttackPower();
        }
        return totalAttack;
    }

    @Override
    public void takeDamage(int amount) {
        List<CombatNode> aliveChildren = getAliveChildren();
        int appliedDMG = amount/aliveChildren.size();
        for(CombatNode unit : aliveChildren){
            unit.takeDamage(appliedDMG);
        }
    }

    @Override
    public boolean isAlive() {
        boolean groupAlive = false;
        for(CombatNode unit : children){
            groupAlive |= unit.isAlive();
        }
        return groupAlive;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        System.out.printf(indent + "►" + name + "\n");
        for(CombatNode unit : children){
            unit.printTree(indent+"   ");
        }
    }

    private List<CombatNode> getAliveChildren() {
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
        return aliveChildren;
    }
}
