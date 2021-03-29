package com.example.android.codelabs.paging.accounts

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.anarads.R
import com.example.anarads.api.APIService
import com.example.anarads.api.ApiUtils
import com.example.anarads.category.CategoryFragmentDirections
import com.example.anarads.model.Token
import kotlinx.android.synthetic.main.accounts_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AccountsRegister : Fragment(){

    lateinit var context: AppCompatActivity
    val TAG = "AccountsLogin"

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
        this.context = context as AppCompatActivity
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

        val view: View = inflater.inflate(R.layout.accounts_register,
                container, false)

        val button_register_save = view.findViewById<View>(R.id.button_register_save) as Button

        button_register_save.setOnClickListener {

            var mAPIService: APIService? = null

            mAPIService = ApiUtils.apiService

            mAPIService!!.accountsRegister(ed_username_reg.text.toString(), ed_password_reg.text.toString(),ed_email_reg.text.toString()).enqueue(object : Callback<Token> {

                override fun onResponse(call: Call<Token>, response: Response<Token>) {

                    if (response.isSuccessful()) {


                        findNavController().navigate(R.id.accountsTab)

                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {

                }
            })



        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG,"onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }


}