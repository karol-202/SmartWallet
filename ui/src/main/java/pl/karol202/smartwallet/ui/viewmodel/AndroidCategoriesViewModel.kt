package pl.karol202.smartwallet.ui.viewmodel

import pl.karol202.smartwallet.presentation.viewmodel.categories.CategoriesViewModel

class AndroidCategoriesViewModel(private val delegate: CategoriesViewModel) : AndroidViewModelProxy(delegate),
                                                                              CategoriesViewModel by delegate
