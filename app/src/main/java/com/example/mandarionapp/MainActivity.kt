package com.example.mandarionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    private lateinit var viewPager: ViewPager
    private lateinit var pages: List<Page>
    private lateinit var pagerAdapter: MainPagerAdapter
    private lateinit var mainTab: TabLayout
    private lateinit var bottom_sheet_register: CoordinatorLayout
    private lateinit var bottom_sheet_login: CoordinatorLayout
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        viewPager = findViewById(R.id.wp_main)
        mainTab = findViewById(R.id.tab_main)

        val btn_started: Button = findViewById(R.id.btn_started)
        val btn_login: Button = findViewById(R.id.btn_login)

        bottom_sheet_register = findViewById(R.id.register_bottom_sheet)
        bottom_sheet_login = findViewById(R.id.login_bottom_sheet)

        val sheetBehaviorReg = BottomSheetBehavior.from(bottom_sheet_register)
        val sheetBehaviorLog = BottomSheetBehavior.from(bottom_sheet_login)

        sheetBehaviorReg.state = BottomSheetBehavior.STATE_HIDDEN
        sheetBehaviorLog.state = BottomSheetBehavior.STATE_HIDDEN

        pages = addPages()
        refreshAdapter()

        mainTab.setupWithViewPager(viewPager)
        addAutoScrollToViewPager()

        btn_started.setOnClickListener {
            if (sheetBehaviorReg.state == BottomSheetBehavior.STATE_HIDDEN) {
                sheetBehaviorReg.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
//                sheetBehaviorReg.state = BottomSheetBehavior.STATE_EXPANDED
                sheetBehaviorReg.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        btn_login.setOnClickListener {
            if (sheetBehaviorLog.state == BottomSheetBehavior.STATE_HIDDEN) {
                sheetBehaviorLog.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
//                sheetBehaviorLog.state = BottomSheetBehavior.STATE_EXPANDED
                sheetBehaviorLog.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun refreshAdapter() {
        pagerAdapter = MainPagerAdapter(pages, this)
        viewPager.adapter = pagerAdapter
    }


    private fun addAutoScrollToViewPager() {
        val DELAY_MS: Long = 1000 //delay in milliseconds before task is to be executed

        val PERIOD_MS: Long = 3000 // time in milliseconds between successive task executions.

        val handler = Handler()
        val update = Runnable {
            if (viewPager.currentItem == pagerAdapter.count - 1) {
                viewPager.currentItem = 0
            } else {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            }
        }

        timer = Timer()

        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    private fun addPages(): ArrayList<Page> {
        return ArrayList<Page>().apply {
            this.add(Page("Payment", "You can pay for bills and purchase in markets"))
            this.add(Page("Transfer", "You can transfer money to other card holders"))
            this.add(
                Page(
                    "Extra payments",
                    "You can pay for cellular, utility, transportation services"
                )
            )
        }
    }
}