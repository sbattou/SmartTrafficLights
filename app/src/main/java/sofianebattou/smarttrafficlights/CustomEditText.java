package sofianebattou.smarttrafficlights;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Emmanuel on 2017-09-19.
 */

    class CustomEditText extends android.support.v7.widget.AppCompatEditText{

    public CustomEditText(Context context) {
        super(context);
        ((Menu)context).setCustomEditText(this);
        invalidate();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((Menu)context).setCustomEditText(this);
        invalidate();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((Menu)context).setCustomEditText(this);
        invalidate();
    }
}
