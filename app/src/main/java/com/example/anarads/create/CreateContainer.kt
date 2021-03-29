package com.example.anarads.create


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.anarads.R


class CreateContainer : Fragment(){

    lateinit var context: AppCompatActivity

    val post_types = arrayOf("Universal", "Avto")
    private lateinit var adapter: ArrayAdapter<String>


    val TAG = "CreateContainer"

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

        val view: View = inflater.inflate(R.layout.create_container,
                container, false)



        val spinner = view.findViewById<Spinner>(R.id.spinner_type)

        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, post_types)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define you actions as you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                if (position === 0) {
                    val fm = context.supportFragmentManager
                    val fragmentTransaction: FragmentTransaction
                    val fragment = CreatePost()
                    fragmentTransaction = fm.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                    fragmentTransaction.commit()
                }

                if (position === 1) {
                    val fm = context.supportFragmentManager
                    val fragmentTransaction: FragmentTransaction
                    val fragment = CreateCar()
                    fragmentTransaction = fm.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                    fragmentTransaction.commit()
                }


            }
        }



        return view


    }






    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG,"onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG,"onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG,"onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG,"onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG,"onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG,"onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG,"onDetach")
        super.onDetach()
    }
}