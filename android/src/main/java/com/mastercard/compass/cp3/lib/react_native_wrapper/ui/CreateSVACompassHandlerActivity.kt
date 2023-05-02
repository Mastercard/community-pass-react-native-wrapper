package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.sva.EVoucherSVA
import com.mastercard.compass.model.sva.EVoucherType
import com.mastercard.compass.model.sva.FinancialSVA
import com.mastercard.compass.model.sva.SVA

class CreateSVACompassApiHandlerActivity: CompassApiHandlerActivity<String>() {

  lateinit var sva: SVA

  override suspend fun callCompassApi() {
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val rId: String = intent.getStringExtra(Key.RID) as String
    val type: String = intent.getStringExtra(Key.TYPE) as String
    val unit: String = intent.getStringExtra(Key.UNIT) as String
    when (type){
      "FinancialSVA" ->  {
        sva = FinancialSVA(unit = unit)
      }
      "EVoucherSVA" -> {
        val eVoucherType = intent.getStringExtra(Key.E_VOUCHER_TYPE) as String
        if(eVoucherType.equals("Commodity", true)){
          sva = EVoucherSVA(unit = unit, eVoucherType = EVoucherType.COMMODITY)
        } else if(eVoucherType.equals("Point", true)){
          sva = EVoucherSVA(unit = unit, eVoucherType = EVoucherType.POINT)
        }
      }
    }
    val intent = compassKernelServiceInstance.getCreateSVAActivityIntent(programGUID = programGUID, reliantAppGuid = reliantGUID, rID = rId,  sva = sva)
    compassApiActivityResult.launch(intent)
  }
}
