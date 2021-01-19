package pl.karol202.smartwallet.ui.viewmodel

import pl.karol202.smartwallet.presentation.viewmodel.subcategoryedit.SubcategoryEditViewModel

class AndroidSubcategoryEditViewModel(private val delegate: SubcategoryEditViewModel) : AndroidViewModelProxy(delegate),
                                                                                        SubcategoryEditViewModel by delegate
