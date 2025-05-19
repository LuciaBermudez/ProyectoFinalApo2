public class Hero extends Character {
    public Hero(int x, int y) {
        super(x, y, 100, 20); // 100 HP, 20 ataque base
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Habilidad especial: ataque fuerte
    public int specialAttack() {
        return atk * 2;
    }
}
