package com.example.inventoryapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat





class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
           insets
      }
        binding.btnGoToSecondScreen.setOnClickListener {
            log.d("MainActivity", "Navigation request triggered. Processing fields.")
            val name = binding.edtItemName.text.toString().trim()
            val category = binding.edtCategory.text.toString().trim()
            val qtyString = binding.edtQuantity.text.toString().trim()
            val comment = binding.edtComment.text.toString().trim()

            if (name.isEmpty() || category.isEmpty() || qtyString.isEmpty() || comment.isEmpty()){
                Log.w("MainActivity", "Validation failure: Empty data configurations")
                Toast.makeText(this, "Error: All layout input fields must be filled!", Toast.LENGTH_SHORT).show()
                    return@setOnclicklistener
            }

            val quantity = qtyString.toIntorNull()
            if (quantity === null || quantity <=0) {
                Log.w("MainActivity", "Validation failure: Non-numeric quantity parsed")
                binding.edtQuantity.error = "Qunatity  must be positive integer!"
                return@setOnClickListener
            }

            log.i("MainActivity", "Validation pass. Bundling parameters into explicit")
            val intent = Intent(this, ViewListActivity::class.java)
            intent.putExtra("Extra_NAME", name)
            intent.putExtra("Extra_CAT", category)
            intent.putExtra("Extra_QTY", quantity)
            intent.putExtra("Extra_CMT", comment)
            startActivity(intent)

        }

        binding.btnExitApp.setOnClickListener {
            Log.i("MainActivity", "App teardown requested by operational workflow interface")
            finishAffinity()
        }
   }
}