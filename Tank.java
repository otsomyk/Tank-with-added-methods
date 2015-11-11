package tank;

public class Tank {
	private int speed = 10;

	// 1 - up, 2 - down, 3 - left, 4 - right
	private int direction;

	// current position on BF
	private int x;
	private int y;

	private ActionField af;
	private BattleField bf;

	public Tank(ActionField af, BattleField bf) {
		this(af, bf, 128, 512, 1);
	}

	public Tank(ActionField af, BattleField bf, int x, int y, int direction) {
		this.af = af;
		this.bf = bf;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public void turn(int direction) throws Exception {
		this.direction = direction;
		af.processTurn(this);
	}

	public void move(int direction) throws Exception {
		af.processMove(this);

	}

	public void fire() throws Exception {
		Bullet bullet = new Bullet((x + 25), (y + 25), direction);
		af.processFire(bullet);
	}

	public void moveToQuadrant(int v, int h) throws Exception {
		int x = Integer.valueOf(af.getQuadrantXY(v, h).substring(0,
				af.getQuadrantXY(v, h).indexOf("_")));
		int y = Integer.valueOf(af.getQuadrantXY(v, h).substring(
				af.getQuadrantXY(v, h).indexOf("_") + 1,
				af.getQuadrantXY(v, h).length()));
		int i = 0;
		int tankX0 = x;
		int tankY0 = y;

		if (tankY0 < y) {
			while (i < (y - tankY0) / 64) {
				moveAndFire(2);
			}
		} else if (tankY0 > y) {
			while (i < (tankY0 - y) / 64) {
				moveAndFire(1);
			}
		}

		if (tankX0 < x) {
			while (i < (x - tankX0) / 64) {
				moveAndFire(4);
			}
		} else if (tankX0 > x) {
			while (i < (tankX0 - x) / 64) {
				moveAndFire(3);
			}
		}
	}

	void moveAndFire(int direction) throws Exception {
		int i = 0;
		move(direction);
		i++;
		fire();
	}

	int random() {
		long time = System.currentTimeMillis() / 100;
		return (int) (time % 5);
	}

	public void moveRandom() throws Exception {
		int direction = random();
		turn(direction);
		move(direction);
	}

	public void clean() throws Exception {
		for (int i = 1; i <= 9; i++) {
			for (int k = 1; k <= 9; k++) {
				if (!bf.equals(" ")) {
					moveToQuadrant(k, i);
				}
			}
		}
	}

	public void updateX(int x) {
		this.x += x;
	}

	public void updateY(int y) {
		this.y += y;
	}

	public long getDirection() {
		return direction;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeed() {
		return speed;
	}

}
