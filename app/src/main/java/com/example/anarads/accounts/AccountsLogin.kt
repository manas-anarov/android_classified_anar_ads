package com.example.anarads.accounts
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.anarads.R

import com.example.anarads.api.APIService
import com.example.anarads.api.ApiUtils
import com.example.anarads.model.Token
import kotlinx.android.synthetic.main.accounts_login.*
//import kotlinx.android.synthetic.main.accounts_login.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class AccountsLogin : Fragment(){

    val TAG = "AccountsLogin"

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
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

        val view: View = inflater.inflate(R.layout.accounts_login,
                container, false)

        val button_login_save = view.findViewById<View>(R.id.button_login_save) as Button

        button_login_save.setOnClickListener {

            var mAPIService: APIService? = null

            mAPIService = ApiUtils.apiService

            mAPIService!!.accountsLogin(ed_username.text.toString(), ed_password.text.toString()).enqueue(object : Callback<Token> {

                override fun onResponse(call: Call<Token>, response: Response<Token>) {

                    if (response.isSuccessful()) {

                        val getted_token = response.body()!!.token


                        val preferences = activity!!.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                        val prefLoginEdit = preferences.edit()
                        prefLoginEdit.putString("token", getted_token)
                        prefLoginEdit.commit()

//                        val token_ex_ap: String = GetSetData.getMySavedToken(context!!.applicationContext)


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