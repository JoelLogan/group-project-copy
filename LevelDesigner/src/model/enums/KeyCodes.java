package model.enums;

public enum KeyCodes {
    NUMPAD1(97, 96, 106, 138, 128,600),
    NUMPAD2(98, 112, 126, 158,282,600),
    NUMPAD3(99, 128, 146, 178,435,600),
    NUMPAD4(100, 144, 166, 198,128,400),
    NUMPAD5(101, 160, 186, 218,282,400),
    NUMPAD6(102, 176, 206, 238,435,400),
    NUMPAD7(103, 192, 226, 255,128,200),
    NUMPAD8(104, 208, 246, 255,282,200),
    NUMPAD9(105, 198, 233, 250,435,200),
    ESC(27, 0, 0, 0, 0, 0);

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
