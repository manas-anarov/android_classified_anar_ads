import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.anarads.R
import com.example.anarads.accounts.ViewPagerAdapter


import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.google.android.material.tabs.TabLayoutMediator


class AccountsTab : Fragment(){


    lateinit var myContext: Context


    val TAG = "AccountsTab"



    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")

        myContext = context
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.accounts_tab,
                container, false)


        val tabLayout=view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2=view.findViewById<ViewPager2>(R.id.view_pager_2)

        val adapter= ViewPagerAdapter((activity as AppCompatActivity).supportFragmentManager, lifecycle)

        viewPager2.adapter=adapter

        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Login"
                }
                1->{
                    tab.text="Register"
                }
            }
        }.attach()




        return view
    }

}
