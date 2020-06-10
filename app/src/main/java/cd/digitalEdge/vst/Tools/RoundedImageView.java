package cd.digitalEdge.vst.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class RoundedImageView extends AppCompatImageView {
    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            RoundedBitmapDrawable rid = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            rid.setCornerRadius(((float) bitmap.getWidth()) * 0.5f);
            super.setImageDrawable(rid);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            RoundedBitmapDrawable rid = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            rid.setCornerRadius(((float) bitmap.getWidth()) * 0.5f);
            super.setImageDrawable(rid);
        }
    }
}
