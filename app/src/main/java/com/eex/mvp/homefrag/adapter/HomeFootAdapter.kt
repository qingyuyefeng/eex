package com.eex.mvp.homefrag.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.eex.R
import com.eex.domin.entity.home.Home
import com.eex.domin.entity.home.HomeBean2
import com.eex.domin.entity.home.Industry
import com.eex.home.activity.home.NewsDetailsActivity
import com.eex.mvp.homefrag.loader.GlideImageLoader
import com.youth.banner.Banner
import me.bakumon.library.view.BulletinView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/12 17:38
 */
class HomeFootAdapter(
        private val context: Context,
        val adapter1: HomeAdapter1,
        val list: MutableList<HomeBean2>,
        private var noticeList: MutableList<Industry> = ArrayList(),
        private var bannerList: MutableList<String> = ArrayList(),
        private var clicks: Clicks? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setClicks(clicks: Clicks) {
        this.clicks = clicks
    }

    interface Clicks {
        fun tab1Click()
        fun tab2Click()
        fun tab3Click()
        fun tab4Click()
        fun tab5Click()
        fun bodyItemClick(delkey: String)
    }

    fun setBanners(home: Home) {
        if (home.bannerList.isNotEmpty()) {
            bannerList.clear()
            home.bannerList.forEach {
                bannerList.add(it.path!!)
            }
            notifyItemChanged(0)
        }
    }

    fun setNotices(home: Home) {
        if (home.noticeData.官方活动公告.isNotEmpty()) {
            noticeList.clear()
            noticeList.addAll(home.noticeData.官方活动公告)
            notifyItemChanged(0)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 1 else 2
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return if (p1 == 1) {
            HeadHolder(LayoutInflater.from(context).inflate(R.layout.re_home_head, p0, false))
        } else {
            BodyHolder(LayoutInflater.from(context).inflate(R.layout.re_home_item2, p0, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
        when (holder) {
            is HeadHolder -> {

                holder.rv_home1.adapter = adapter1
                holder.rv_home1.layoutManager = GridLayoutManager(context, 3)

                if (noticeList.isNotEmpty()) {
                    val list0 = ArrayList<String>()
                    noticeList.forEach {
                        list0.add(it.title)
                    }
                    holder.home_notices.setAdapter(NoticeAdapter(context, list0))
                    holder.home_notices.setOnBulletinItemClickListener {
                        val notice = noticeList[it]
                        val intent = Intent(context, NewsDetailsActivity::class.java)
                        intent.putExtra("title", notice.title)
                        intent.putExtra("time", notice.createTime)
                        intent.putExtra("content", notice.content)
                        intent.putExtra("user", notice.createUser)
                        context.startActivity(intent)
                    }
                    holder.tv_notice.visibility = View.GONE
                    holder.home_notices.visibility = View.VISIBLE
                }

                holder.tab1.setOnClickListener {
                    clicks?.tab1Click()
                }
                holder.tab2.setOnClickListener {
                    clicks?.tab2Click()
                }
                holder.tab3.setOnClickListener {
                    clicks?.tab3Click()

                }
                holder.tab4.setOnClickListener {
                    clicks?.tab4Click()
                }
                holder.tab5.setOnClickListener {
                    clicks?.tab5Click()
                }
                if (bannerList.isNotEmpty()) {
                    holder.home_banners.setImageLoader(GlideImageLoader())
                    //设置图片集合
                    holder.home_banners.setImages(bannerList)
                    //banner设置方法全部调用完毕时最后调用
                    holder.home_banners.start()

                    holder.iv_default_banner.visibility = View.GONE
                    holder.home_banners.visibility = View.VISIBLE
                }
            }
            is BodyHolder -> {
                val item = list[p1 - 1]
                holder.tv_name1.text = item.delKey!!.split("_")[0]
                holder.tv_name2.text = if (item.delKey!!.contains("_")) "/${item.delKey!!.split("_")[1]}" else "/USDT"
                val higePrice = item.higePrice ?: 0.0
                val fooPrice = item.fooPrice ?: 0.0
                val newPrice = item.newPrice ?: 0.0
                val lastPrice = item.lastPrice ?: 0.0

                holder.tv_price1.text = higePrice.toString()
                holder.tv_price2.text = fooPrice.toString()
                holder.tv_amount.text = if (lastPrice != 0.0)
                    "${(((newPrice - lastPrice) / lastPrice * 10000).toInt()) / 100.0}%"
                else "0%"
                holder.tv_amount.setBackgroundResource(if (newPrice >= lastPrice) R.drawable.corner_solid_small_green else R.drawable.corner_solid_small_red)

                holder.itemView.setOnClickListener {
                    clicks?.bodyItemClick(item.delKey!!)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size + 1

    class HeadHolder(view: View) : RecyclerView.ViewHolder(view) {

        val rv_home1 = view.findViewById<RecyclerView>(R.id.rv_home1)

        val tab1 = view.findViewById<TextView>(R.id.tv_tab1)
        val tab2 = view.findViewById<TextView>(R.id.tv_tab2)
        val tab3 = view.findViewById<TextView>(R.id.tv_tab3)
        val tab4 = view.findViewById<TextView>(R.id.tv_tab4)
        val tab5 = view.findViewById<TextView>(R.id.tv_tab5)

        val tv_notice = view.findViewById<TextView>(R.id.tv_default_notice)
        val iv_default_banner = view.findViewById<ImageView>(R.id.iv_default_banner)
        val home_notices = view.findViewById<BulletinView>(R.id.home_notices)
        val home_banners = view.findViewById<Banner>(R.id.home_banners)

    }

    class BodyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name1 = view.findViewById<TextView>(R.id.tv_name1)
        val tv_name2 = view.findViewById<TextView>(R.id.tv_name2)
        val tv_price1 = view.findViewById<TextView>(R.id.tv_price1)
        val tv_price2 = view.findViewById<TextView>(R.id.tv_price2)
        val tv_amount = view.findViewById<TextView>(R.id.tv_increase_amount)
    }
}