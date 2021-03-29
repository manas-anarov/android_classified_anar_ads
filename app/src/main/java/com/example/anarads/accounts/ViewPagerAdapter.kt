package com.example.anarads.accounts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android.codelabs.paging.accounts.AccountsRegister

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return   when(position){
            0->{
                AccountsLogin()
            }
            1->{
                AccountsRegister()
            }
            else->{
                Fragment()
            }

        }
    }
}