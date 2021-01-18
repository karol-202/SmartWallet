package pl.karol202.smartwallet.ui.viewmodel

import pl.karol202.smartwallet.presentation.viewmodel.categoryedit.CategoryEditViewModel

class AndroidCategoryEditViewModel(private val delegate: CategoryEditViewModel) : AndroidViewModelProxy(delegate),
                                                                                  CategoryEditViewModel by delegate
