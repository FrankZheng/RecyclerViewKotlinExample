package com.example.recyclerviewkotlinexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewkotlinexample.databinding.ActivityMainBinding
import com.example.recyclerviewkotlinexample.databinding.FragmentListBinding
import com.example.recyclerviewkotlinexample.databinding.ItemGroupViewHolderBinding
import com.example.recyclerviewkotlinexample.databinding.ItemViewHolderBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, ListFragment.newInstance())
            .commit()
    }
}


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val adapter by lazy { Adapter(createItems()) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
    }


    private fun createItems() = (0..9999L).map { index ->
        if (index % 10L == 0L)
            GroupItemVO(index, "Group ${index + 1}", (0..10).map { ItemVO(index * 10000, "Item ${it + 1}") })
        else ItemVO(index, "Item ${index + 1}")
    }


    companion object {
        fun newInstance() = ListFragment()
    }


    private class Adapter(var items: List<VO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        enum class ViewType {
            ITEM, GROUP
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (ViewType.values()[viewType]) {
                ViewType.ITEM -> ViewHolder(ItemViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                ViewType.GROUP -> GroupViewHolder(ItemGroupViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(holder){
                is ViewHolder -> holder.bind(items[position] as ItemVO)
                is GroupViewHolder -> holder.bind(items[position] as GroupItemVO)
            }
        }

        override fun getItemCount(): Int = items.size

        override fun getItemViewType(position: Int): Int = when (items[position]) {
            is ItemVO -> ViewType.ITEM.ordinal
            is GroupItemVO -> ViewType.GROUP.ordinal
            else -> throw IllegalArgumentException()
        }


        class ViewHolder(private val binding: ItemViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(vo: ItemVO) {
                binding.text.text = vo.text
            }
        }

        class GroupViewHolder(private val binding: ItemGroupViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
            private val adapter by lazy { Adapter(listOf()) }

            init {
                binding.recyclerView.adapter = adapter
            }

            fun bind(vo: GroupItemVO) {
                adapter.items = vo.items
                adapter.notifyDataSetChanged()
                binding.title.text = vo.text
            }
        }
    }
}

interface VO

data class ItemVO(
    val id: Long,
    val text: String,
) : VO

data class GroupItemVO(
    val id: Long,
    val text: String,
    val items: List<VO>,
) : VO
