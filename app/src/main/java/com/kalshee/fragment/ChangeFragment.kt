package com.kalshee.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.kalshee.R

/**
 * Created by eWeb_A1 on 6/15/2018.
 */
class ChangeFragment :Fragment(),View.OnClickListener
{


    private lateinit var ib_Back:ImageButton;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView : View= inflater.inflate(R.layout.fragment_change_layout, container, false);

        XMl(mView)
        return mView;
    }

    private fun  XMl( view :View)
    {

        ib_Back=view.findViewById(R.id.ib_Back) as ImageButton
        ib_Back.setOnClickListener(this)




    }
    override fun onClick(v: View)
    {

        when(v.id)
        {

            R.id.ib_Back->{

               activity!!.onBackPressed();

            }
        }

    }

}