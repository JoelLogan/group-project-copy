package model.enums;

public enum KeyCodes {
    ESC(27, 0, 0, 0, 0, 0),
    SHIFT(16, 0, 0, 0, 0, 0),
    NUMPAD1(97, 128, 255, 0, 128, 600),
    NUMPAD2(98, 255, 128, 0, 282, 600),
    NUMPAD3(99, 0, 255, 255, 435, 600),
    NUMPAD4(100, 180, 0, 255, 128, 400),
    NUMPAD5(101, 0, 0, 255, 282, 400),
    NUMPAD6(102, 0, 128, 0, 435, 400),
    NUMPAD7(103, 255, 0, 0, 128, 200),
    NUMPAD8(104, 255, 255, 0, 282, 200),
    NUMPAD9(105, 255, 0, 255, 435, 200);

    private int value, translateX, translateY;
    private double colorR, colorG, colorB;

    private KeyCodes(int value, double colorR, double colorG, double colorB, int translateX, int translateY) {
        this.value = value;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.translateX = translateX;
        this.translateY = translateY;
    }

    public int getValue() {
        return this.value;
    }

    public double getColorRed() {
        return this.colorR / 255.0;
    }

    public double getColorGreen() {
        return this.colorG / 255.0;
    }

    public double getColorBlue() {
        return this.colorB / 255.0;
    }

    public int getTranslateX() {
        return this.translateX;
    }

    public int getTranslateY() {
        return this.translateY;
    }
}
