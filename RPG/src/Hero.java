public class Hero extends Character {
    public Hero(int x, int y) {
        super(x, y, 100, 20);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int specialAttack() {
        return atk * 2;
    }

    public void receiveHealing(int amount) {
        this.hp += amount;
        if (this.hp > 100) this.hp = 100;
    }
    
    public void heal(int amount) {
    this.hp += amount;
    if (this.hp > 100) {
        this.hp = 100; // Límite máximo de vida
    }
}

}

