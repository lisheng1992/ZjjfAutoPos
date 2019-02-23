package com.zjjf.autopos.custemview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class EnhanceEditText extends EditText {

    public EnhanceEditText(Context context) {
        super(context);
    }

    public EnhanceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnhanceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {

        int action = event.getAction();

        if (keyCode >= KeyEvent.KEYCODE_0
                && keyCode <= KeyEvent.KEYCODE_9) {

            if (KeyEvent.ACTION_DOWN == action) {
                // 直接添加数字
                append(Integer.toString((keyCode - KeyEvent.KEYCODE_0)));
            }
            return true;
        } else if (keyCode >= KeyEvent.KEYCODE_A
                && keyCode <= KeyEvent.KEYCODE_Z) {

            if (KeyEvent.ACTION_DOWN == action) {
                // 针对字母的情况
                char startCase = isLowerCase(event) ? 'a' : 'A';
                append(Character.toString((char) (startCase + keyCode - KeyEvent.KEYCODE_A)));
            }
            return true;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    /**
     * 判断当前是小字模式
     * @param event
     * @return
     */
    private boolean isLowerCase(KeyEvent event) {

        boolean capsLock = event.isCapsLockOn();
        boolean shiftPressed = event.isShiftPressed();

        if ((!capsLock && shiftPressed)
                || (capsLock && !shiftPressed)) {
            return false;
        }
        return true;
    }
}
