public abstract class Character {
    protected int x, y;
    protected int hp;
    protected int atk;

    public Character(int x, int y, int hp, int atk) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.atk = atk;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getHp() { return hp; }
    public int getAtk() { return atk; }

    public boolean isAlive() {
        return hp > 0;
    }

    public void receiveDamage(int damage) {
        hp -= damage;
    }
}

