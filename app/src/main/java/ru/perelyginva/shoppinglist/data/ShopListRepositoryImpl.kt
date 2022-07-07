package ru.perelyginva.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.perelyginva.shoppinglist.domain.ShopItem
import ru.perelyginva.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    // создаем объект который мы возвращаем
    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>(Comparator<ShopItem>
    { o1, o2 -> o1.id.compareTo(o2.id) })
    private var autoIncrementId = 0

    init {

        for (i in 0 until 10) {
            val item = ShopItem("Name $i", i, true)
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        // устанавливаем у нашего элемента id и увеличиваем его на один
        //проверяем, какой id содержит элемент
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
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
        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData //возвращаем измененную коллекцию
    }

    //созадем функцию для обновления liveData
    private fun updateList() {
        shopListLiveData.value = shopList.toList()
    }
}