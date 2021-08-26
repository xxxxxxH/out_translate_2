package net.basicmodel

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.layout_bottom.*
import net.fragment.CommonFragment
import net.fragment.TranslateFragment
import net.utils.Constant

class MainActivity : AppCompatActivity() {
    var translateF: TranslateFragment? = null
    var collectF: CommonFragment? = null
    var historyF: CommonFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MMKV.initialize(this)
        showPosition(0)
        initView()
    }

    private fun initView() {
        tv1.setOnClickListener {
            showPosition(0)
            setColor(0)
        }
        tv2.setOnClickListener {
            showPosition(1)
            setColor(1)
        }
        tv3.setOnClickListener {
            showPosition(2)
            setColor(2)
        }
    }

    private fun showPosition(position: Int) {
        val fm: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        hideAll(ft)
        if (position == 0) {
            translateF = fm.findFragmentByTag(Constant.TAG_TRANSLATE) as TranslateFragment?
            if (translateF == null) {
                translateF = TranslateFragment()
                ft.add(R.id.content, translateF!!, Constant.TAG_TRANSLATE)
            } else {
                ft.show(translateF!!)
            }
        }

        if (position == 1) {
            collectF = fm.findFragmentByTag(Constant.TAG_COLLECTION) as CommonFragment?
            if (collectF == null) {
                collectF = CommonFragment()
                ft.add(R.id.content, collectF!!, Constant.TAG_COLLECTION)
            } else {
                ft.show(collectF!!)
            }
        }

        if (position == 2) {
            historyF = fm.findFragmentByTag(Constant.TAG_HISTORY) as CommonFragment?
            if (historyF == null) {
                historyF = CommonFragment()
                ft.add(R.id.content, historyF!!, Constant.TAG_HISTORY)
            } else {
                ft.show(historyF!!)
            }
        }
        ft.commit()
    }

    private fun hideAll(ft: FragmentTransaction) {
        if (translateF != null) {
            ft.hide(translateF!!)
        }
        if (collectF != null) {
            ft.hide(collectF!!)
        }
        if (historyF != null) {
            ft.hide(historyF!!)
        }
    }

    private fun setColor(position: Int){
        when(position){
            0->{
                tv1.setTextColor(Color.RED)
                tv2.setTextColor(Color.BLACK)
                tv3.setTextColor(Color.BLACK)
            }
            1->{
                tv1.setTextColor(Color.BLACK)
                tv2.setTextColor(Color.RED)
                tv3.setTextColor(Color.BLACK)
            }
            2->{
                tv1.setTextColor(Color.BLACK)
                tv2.setTextColor(Color.BLACK)
                tv3.setTextColor(Color.RED)
            }
        }
    }

}