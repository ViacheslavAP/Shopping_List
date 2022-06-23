package ru.perelyginva.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.perelyginva.shoppinglist.data.ShopListRepositoryImpl
import ru.perelyginva.shoppinglist.domain.DeleteShopUseCase
import ru.perelyginva.shoppinglist.domain.EditShopItemUseCase
import ru.perelyginva.shoppinglist.domain.GetShopListUseCase
import ru.perelyginva.shoppinglist.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopUseCase = DeleteShopUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopList(shopItem: ShopItem){
        deleteShopUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enable = !shopItem.enable)
    }
}