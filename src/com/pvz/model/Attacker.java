package com.pvz.model;

public interface Attacker<T extends Damageable> {
	void attack(T target);
}
