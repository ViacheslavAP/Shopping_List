package ru.perelyginva.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import ru.perelyginva.shoppinglist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel//инициализируем
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //присваиваем значение viewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java ]
        viewModel.shopList.observe(this){
            Log.d("MainActivity", it.toString() )
            //проверка для того чтобы удаление произошло один раз
            if (count == 0){
                count++
                val item = it[0]
                viewModel.changeEnableState(item )
            }
        }

    }

}