package ru.perelyginva.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.perelyginva.shoppinglist.domain.ShopItem
import ru.perelyginva.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    // создаем объект который мы возвращаем
    private val shopListLiveD = MutableLiveData<List<ShopItem>>()

    override fun addShopItem(shopItem: ShopItem) {
        // устанавливаем у нашего элемента id и увеличиваем его на один
        //проверяем, какой id содержит элемент
        if (shopItem.id == ShopItem.UNDEFINED_ID){ shopItem.id = autoIncrementId++ }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
       val oldElement = getShopItem(shopItem.id) // находим элемент
        shopList.remove(oldElement)// удаляем элемент
        addShopItem(shopItem)//добавляем новый элемент
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        //ищем элемент по его id
       return shopList.find { it.id == shopItemId } ?:
       throw RuntimeException("element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveD //возвращаем измененную коллекцию
    }

    //созадем функцию для обновления liveData
    private fun updateList(){
        shopListLiveD.value = shopList.toList()
    }
}