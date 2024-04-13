package mobile.week07.dialogtest

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import mobile.week07.dialogtest.databinding.ActivityMainBinding
import mobile.week07.dialogtest.databinding.DialogInterfaceBinding
import java.time.DayOfWeek

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding  //기본 세팅

    val food = arrayOf("김치찌개", "짜장면", "마라탕", "샌드위치")
    var checkedItem = 0
    var foodChecked = booleanArrayOf(false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)  //기본 세팅
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)  //기본 세팅
        setContentView(binding.root)  //기본 세팅

//        binding.button.setOnClickListener( object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                val toast = Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT)  //this: object객체 가리킴 그래서 명확하게 표현해줘야 함
//                toast.show()
//            }
//        } )
        binding.button.setOnClickListener {
            val dialogBinding = DialogInterfaceBinding.inflate(layoutInflater)  //inflation: xml을 java객체로 바꿔주는 작업
//            val toast = Toast.makeText(this, "Click", Toast.LENGTH_SHORT)  //람다함수에서 '@MainActivity' 생략 가능
//            toast.show()
//            Toast.makeText(this, "Click", Toast.LENGTH_SHORT)  //그냥 이렇게 바로 호출해도 됨

//            DatePickerDialog(this, object: DatePickerDialog.OnDateSetListener {
//                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//                    Log.d("MainActicity", "$year/$month/$dayOfMonth")
//                }
//            } , 2023, 3, 19).show()

//            TimePickerDialog(this, object: TimePickerDialog.OnTimeSetListener {
//                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
//                    Log.d(TAG, "$hourOfDay:$minute")
//                }
//            }, 15, 0, true).show()

            AlertDialog.Builder(this).run {
                setTitle("test dialog")
                setIcon(android.R.drawable.ic_dialog_info)
//                setMessage("정말 종료하시겠습니까?")
//                목록
//                setItems(food, object: DialogInterface.OnClickListener {
//                    override fun onClick(dialog: DialogInterface? whcih: Int) {
//                        Toast.makeText(this@MainActivity, food[which], Toast.LENGTH_SHORT).show()
//                    }
//                })
//                라디오 버튼
//                setSingleChoiceItems(food, 0, object: DialogInterface.OnClickListener {
////                    override fun onClick(dialog: DialogInterface? whcih: Int) {
////                        Toast.makeText(this@MainActivity, food[which], Toast.LENGTH_SHORT).show()
////                    }
////                })
//                setSingleChoiceItems(food, ckeckedItem, object: DialogInterface)
//                여러개 선택 라디오 버튼
//                setMultiChoiceItems(food, foodChecked, object: DialogInterface.OnMultiChoiceClickListener {
//                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
//                        foodChecked[which] = isChecked
//                        Toast.makeText(this@MainActivity, food[which] + isChecked, Toast.LENGTH_SHORT).show()
//                    }
//                })
                
//                직접 만든 화면
                setView(dialogBinding.root)

                setPositiveButton("OK", object: DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(this@MainActivity, "Click!!!", Toast.LENGTH_SHORT).show()
                    }
                })
                setNegativeButton("Cancel", null)
                setNeutralButton("More", null)  //null: 동작 지정 x, 그냥 창 닫음

//                setCancelable(false)  //직접 만든 창 바깥쪽 눌러도 안 닫힘

                show()
            }
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("~~")

//            AlertDialog.Builder(this)
//                .setTitle("~~~")
//                .setIcon(android.R.drawable.ic_dialog_info)  //자신을 반환(builder 반환)하기에 이렇게 써도 됨
        }
    }
}