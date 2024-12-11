package com.example.property.builder.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.property.R
import com.example.property.databinding.BuilderInquiryItemlistBinding
import com.example.property.model.InquiryModel

class BuilderInquiryAdapter(private var itemList: ArrayList<InquiryModel>) :
    RecyclerView.Adapter<BuilderInquiryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BuilderInquiryItemlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: BuilderInquiryItemlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                showInputDialog(binding.root.context, itemList[adapterPosition])
            }
        }

        fun bind(item: InquiryModel) {
            binding.inquiryId.text = "${item.id}"
            binding.date.text = "${item.date}"
            binding.userName.text = "${item.userName}"
            binding.userEmail.text = "${item.email}"
            binding.title.text = "For: ${item.title}"
            binding.iDesc.text = "Inquiry Message: ${item.desc}"
        }

        private fun showInputDialog(context: Context, inquiryModel: InquiryModel) {
            val dialogBuilder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogView = inflater.inflate(R.layout.inquiry_answer_dialog_layout, null)
            dialogBuilder.setView(dialogView)


            val inquiryAnswerEditText = dialogView.findViewById<EditText>(R.id.editTextInquiryAnswer)

            dialogBuilder.setTitle("Enter Inquiry Answer")
                .setPositiveButton("Submit") { _, _ ->
                    val inquiryAnswer = inquiryAnswerEditText.text.toString()

                    // Do something with the data, for example, send it to a listener or perform an action
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }

            val alertDialog = dialogBuilder.create()
            alertDialog.show()
        }
    }
}
