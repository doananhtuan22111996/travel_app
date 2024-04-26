package vn.travel.data.model

import vn.travel.domain.model.BaseModel

abstract class BaseRaw {
    abstract fun raw2Model(): BaseModel?
}
