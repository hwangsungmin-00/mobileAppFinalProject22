package com.example.finalproject

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.ActivityCalculateBinding
import com.example.finalproject.databinding.CalculateAddBinding

class CalculateActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalculateBinding
    lateinit var todoAdd: CalculateAddBinding

    var datas: MutableList<CalculateItem>? = mutableListOf<CalculateItem>(
        CalculateItem("떡볶이", "4000",  "간식"),
        CalculateItem("돈까스", "7000",  "저녁")
    )
    lateinit var adapter: CalculteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_to_do)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdd = CalculateAddBinding.inflate(layoutInflater)

        // 리사이클러뷰 설정
        val layoutManager = LinearLayoutManager(this)
        binding.calculateRecyclerView.layoutManager = layoutManager
        adapter = CalculteAdapter(datas)
        binding.calculateRecyclerView.adapter = adapter

        // 모달창에서 저장/취소 버튼 눌렀을 때 발생하는 이벤트
        val save = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1== DialogInterface.BUTTON_POSITIVE) { // [저장] 버튼을 눌렀을 경우

                    // 입력값을 id로 하나하나 찾아와서 [데이터]에 저장해야 함
                    Log.d("budgetApp", "행 추가 저장하기")
                    datas?.add(CalculateItem(todoAdd.calculatework.text.toString(),
                        todoAdd.calculaterole.text.toString(),
                        //categoryString[todoAdd.category.selectedItemId.toInt()],
                        //todoAdd.todosetting.text.toString()
                    ))

                    // 간격+단위 어떻게 가져오지

                    // 리사이클러뷰 업데이트
                    adapter.notifyItemInserted(adapter.itemCount)

                    // 초기화 - null이 입력되면 오류남, 처리 필요
                    todoAdd.calculatework.setText("")
                    todoAdd.calculaterole.setText("")
                    //todoAdd.category.setSelection(0)
                    //todoAdd.todosetting.setText("7")

                    // 합계 칸 업데이트
                    //binding.sumResult.setText(getSum().toString())
                }
                else if (p1== DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("budgetApp", "행 추가 취소")
                }
            }
        }

        // 새 항목 추가 버튼 - 대화상자 열림
        val alert = AlertDialog.Builder(this)
            .setTitle("예산서 추가")
            .setView(todoAdd.root)
            .setPositiveButton("저장", save)
            .setNegativeButton("취소", save)
            .create()

        binding.calculateAdd.setOnClickListener {
            alert.show()
        }
    }
}