import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.property.R
import com.example.property.loginRegister.MainActivity

class IntroductionPagerAdapter(private val context: Context,private val activity: Activity,private val viewPager: ViewPager) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(getLayoutResource(position), container, false)
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return 3 // Number of pages
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    // This method is used to navigate to the next page
    fun moveToNextPage() {
        val nextItem = viewPager.currentItem + 1
        if (nextItem < count) {
            viewPager.currentItem = nextItem
        }else{


            val intent = Intent(activity, MainActivity::class.java)
            (context as AppCompatActivity).overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
            activity.startActivity(intent)
            activity.finish()
        }
    }

    private fun getLayoutResource(position: Int): Int {
        return when (position) {
            0 -> R.layout.page1_layout
            1 -> R.layout.page2_layout
            2 -> R.layout.page3_layout
            else -> R.layout.page1_layout
        }
    }
}
