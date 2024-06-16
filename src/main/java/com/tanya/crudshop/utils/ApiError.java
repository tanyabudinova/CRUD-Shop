package com.tanya.crudshop.utils;

import java.time.LocalDateTime;

public record ApiError(Integer status, String message) {
}
